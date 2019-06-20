import random
import sys

import numpy as np
from keras.layers import Dense
from keras.models import Sequential, load_model

import utils

# Performs classification using Logistic Regression.


def build_model():
    model = Sequential()
    model.add(Dense(1, input_dim=utils.VOCAB_SIZE, activation='sigmoid'))
    model.compile(loss='binary_crossentropy',
                  optimizer='adam', metrics=['accuracy'])
    return model


def evaluate_model(model, val_tweets):
    correct, total = 0, len(val_tweets)
    for val_set_X, val_set_y in utils.extract_features(val_tweets, feat_type=utils.FEAT_TYPE, test_file=False):
        prediction = model.predict_on_batch(val_set_X)
        prediction = np.round(prediction)
        correct += np.sum(prediction == val_set_y[:, None])
    return float(correct) / total


if __name__ == '__main__':
    service_port = int(sys.argv[1])
    np.random.seed(1337)
    utils.init_ngrams()
    tweets = utils.process_tweets(utils.TRAIN_PROCESSED_FILE, service_port, test_file=False)
    if utils.TRAIN:
        train_tweets, val_tweets = utils.split_data(tweets)
    else:
        random.shuffle(tweets)
        train_tweets = tweets
    del tweets
    print('Extracting features & training batches')
    nb_epochs = 20
    batch_size = 500
    model = build_model()
    n_train_batches = int(np.ceil(len(train_tweets) / float(batch_size)))
    best_val_acc = 0.0
    for j in range(nb_epochs):
        i = 1
        for training_set_X, training_set_y in utils.extract_features(train_tweets, feat_type=utils.FEAT_TYPE, batch_size=batch_size, test_file=False):
            o = model.train_on_batch(training_set_X, training_set_y)
            sys.stdout.write('\rIteration %d/%d, loss:%.4f, acc:%.4f' %
                             (i, n_train_batches, o[0], o[1]))
            sys.stdout.flush()
            i += 1
        val_acc = evaluate_model(model, val_tweets)
        print('Epoch: %d, val_acc:%.4f' % (j + 1, val_acc))
        random.shuffle(train_tweets)
        if val_acc > best_val_acc:
            print('Accuracy improved from %.4f to %.4f, saving model' % (best_val_acc, val_acc))
            best_val_acc = val_acc
            model.save('best_model.h5')
    print('Testing')
    del train_tweets
    del model
    model = load_model('best_model.h5')
    test_tweets = utils.process_tweets(utils.TEST_PROCESSED_FILE, service_port, test_file=True)
    n_test_batches = int(np.ceil(len(test_tweets) / float(batch_size)))
    predictions = np.array([])
    print('Predicting batches')
    i = 1
    for test_set_X, _ in utils.extract_features(test_tweets, feat_type=utils.FEAT_TYPE, batch_size=batch_size, test_file=True):
        prediction = np.round(model.predict_on_batch(test_set_X).flatten())
        predictions = np.concatenate((predictions, prediction))
        i += 1
    predictions = [(str(j), int(predictions[j]))
                   for j in range(len(test_tweets))]
    utils.save_results_to_csv(predictions, 'logistic.csv')
    print('Saved to logistic.csv')
