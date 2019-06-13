import sys

from utils import file_to_wordset, parse_csv, save_results_to_csv

# Classifies a tweet based on the number of positive and negative words in it

# TRAIN_PROCESSED_FILE = 'code/train-processed.csv'
TRAIN_PROCESSED_FILE = 'dataset/kaggle-sentiment140-training.csv'
# TEST_PROCESSED_FILE = 'code/test-processed.csv'
TEST_PROCESSED_FILE = 'dataset/kaggle-sentiment140-testing.csv'
POSITIVE_WORDS_FILE = 'dataset/positive-words.txt'
NEGATIVE_WORDS_FILE = 'dataset/negative-words.txt'
TRAIN = True


def classify(processed_csv, service_port, test_file=True, **params):
    positive_words = file_to_wordset(params.pop('positive_words'), service_port)
    negative_words = file_to_wordset(params.pop('negative_words'), service_port)
    predictions = []
    for row in parse_csv(processed_csv, service_port):
        tweet_id = row.field[1]
        tweet = row.field[2]
        if not test_file:
            label = row.field[0]
        pos_count, neg_count = 0, 0
        for word in tweet.split():
            if word in positive_words:
                pos_count += 1
            elif word in negative_words:
                neg_count += 1
        # print pos_count, neg_count
        prediction = 1 if pos_count >= neg_count else 0
        if test_file:
            predictions.append((tweet_id, prediction))
        else:
            predictions.append((tweet_id, int(label), prediction))
    return predictions


if __name__ == '__main__':
    service_port = int(sys.argv[1])
    if TRAIN:
        predictions = classify(TRAIN_PROCESSED_FILE, service_port, test_file=(not TRAIN), positive_words=POSITIVE_WORDS_FILE, negative_words=NEGATIVE_WORDS_FILE)
        correct = sum([1 for p in predictions if p[1] == p[2]]) * 100.0 / len(predictions)
        print('Correct = %.2f%%' % correct)
    else:
        predictions = classify(TEST_PROCESSED_FILE, service_port, test_file=(not TRAIN), positive_words=POSITIVE_WORDS_FILE, negative_words=NEGATIVE_WORDS_FILE)
        save_results_to_csv(predictions, 'baseline.csv')
