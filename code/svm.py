import random
import sys

import numpy as np
from sklearn import svm

import utils

# Performs classification using SVM.


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
    clf = svm.LinearSVC(C=0.1)
    batch_size = len(train_tweets)
    i = 1
    n_train_batches = int(np.ceil(len(train_tweets) / float(batch_size)))
    for training_set_X, training_set_y in utils.extract_features(train_tweets, test_file=False, feat_type=utils.FEAT_TYPE, batch_size=batch_size, is_sparse=True):
        utils.write_status(i, n_train_batches)
        i += 1
        if utils.FEAT_TYPE == 'frequency':
            tfidf = utils.apply_tf_idf(training_set_X)
            training_set_X = tfidf.transform(training_set_X)
        clf.fit(training_set_X, training_set_y)
    print('\n')
    print('Testing')
    if utils.TRAIN:
        correct, total = 0, len(val_tweets)
        i = 1
        batch_size = len(val_tweets)
        n_val_batches = int(np.ceil(len(val_tweets) / float(batch_size)))
        for val_set_X, val_set_y in utils.extract_features(val_tweets, test_file=False, feat_type=utils.FEAT_TYPE, batch_size=batch_size, is_sparse=True):
            if utils.FEAT_TYPE == 'frequency':
                val_set_X = tfidf.transform(val_set_X)
            prediction = clf.predict(val_set_X)
            correct += np.sum(prediction == val_set_y)
            utils.write_status(i, n_val_batches)
            i += 1
        print('\nCorrect: %d/%d = %.4f %%' % (correct, total, correct * 100. / total))
    else:
        del train_tweets
        test_tweets = utils.process_tweets(TEST_PROCESSED_FILE, service_port, test_file=True)
        n_test_batches = int(np.ceil(len(test_tweets) / float(batch_size)))
        predictions = np.array([])
        print('Predicting batches')
        i = 1
        for test_set_X, _ in utils.extract_features(test_tweets, test_file=True, feat_type=utils.FEAT_TYPE, is_sparse=True):
            if utils.FEAT_TYPE == 'frequency':
                test_set_X = tfidf.transform(test_set_X)
            prediction = clf.predict(test_set_X)
            predictions = np.concatenate((predictions, prediction))
            utils.write_status(i, n_test_batches)
            i += 1
        predictions = [(str(j), int(predictions[j]))
                       for j in range(len(test_tweets))]
        utils.save_results_to_csv(predictions, 'svm.csv')
        print('\nSaved to svm.csv')
