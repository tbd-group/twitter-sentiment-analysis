import csv
import re
import sys

from nltk.stem.porter import PorterStemmer

from utils import parse_csv


def preprocess_word(word):
    # Remove punctuation
    word = word.strip('\'"?!,.():;')
    # Convert more than 2 letter repetitions to 2 letter
    # funnnnny --> funny
    word = re.sub(r'(.)\1+', r'\1\1', word)
    # Remove - & '
    word = re.sub(r'(-|\')', '', word)
    return word


def is_valid_word(word):
    # Check if word begins with an alphabet
    return (re.search(r'^[a-zA-Z][a-z0-9A-Z\._]*$', word) is not None)


def handle_emojis(tweet):
    # Smile -- :), : ), :-), (:, ( :, (-:, :')
    tweet = re.sub(r'(:\s?\)|:-\)|\(\s?:|\(-:|:\'\))', ' EMO_POS ', tweet)
    # Laugh -- :D, : D, :-D, xD, x-D, XD, X-D
    tweet = re.sub(r'(:\s?D|:-D|x-?D|X-?D)', ' EMO_POS ', tweet)
    # Love -- <3, :*
    tweet = re.sub(r'(<3|:\*)', ' EMO_POS ', tweet)
    # Wink -- ;-), ;), ;-D, ;D, (;,  (-;
    tweet = re.sub(r'(;-?\)|;-?D|\(-?;)', ' EMO_POS ', tweet)
    # Sad -- :-(, : (, :(, ):, )-:
    tweet = re.sub(r'(:\s?\(|:-\(|\)\s?:|\)-:)', ' EMO_NEG ', tweet)
    # Cry -- :,(, :'(, :"(
    tweet = re.sub(r'(:,\(|:\'\(|:"\()', ' EMO_NEG ', tweet)
    return tweet


def preprocess_tweet(tweet):
    processed_tweet = []
    # Convert to lower case
    tweet = tweet.lower()
    # Replaces URLs with the word URL
    tweet = re.sub(r'((www\.[\S]+)|(https?://[\S]+))', ' URL ', tweet)
    # Replace @handle with the word USER_MENTION
    tweet = re.sub(r'@[\S]+', 'USER_MENTION', tweet)
    # Replaces #hashtag with hashtag
    tweet = re.sub(r'#(\S+)', r' \1 ', tweet)
    # Remove RT (retweet)
    tweet = re.sub(r'\brt\b', '', tweet)
    # Replace 2+ dots with space
    tweet = re.sub(r'\.{2,}', ' ', tweet)
    # Strip space, " and ' from tweet
    tweet = tweet.strip(' "\'')
    # Replace emojis with either EMO_POS or EMO_NEG
    tweet = handle_emojis(tweet)
    # Replace multiple spaces with a single space
    tweet = re.sub(r'\s+', ' ', tweet)
    words = tweet.split()

    for word in words:
        word = preprocess_word(word)
        if is_valid_word(word):
            if use_stemmer:
                word = str(porter_stemmer.stem(word))
            processed_tweet.append(word)

    return ' '.join(processed_tweet)


def preprocess_csv(csv_file_name, processed_file_name, service_port, test_file=False):
    with open(processed_file_name, 'w') as save_to_file:
        csv_writer = csv.writer(save_to_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
        for num_tweets, row in enumerate(parse_csv(csv_file_name, service_port)):
            sentiment = int(row.field[0])
            tweet_id = row.field[1][1:-1]
            text = row.field[2]
            processed_text = preprocess_tweet(text)
            if not test_file:
                csv_writer.writerow([tweet_id, sentiment, processed_text])
            else:
                csv_writer.writerow([tweet_id, processed_text])
    print(f'Saved [{1+num_tweets}] processed tweets to [{processed_file_name}]')
    return processed_file_name


if __name__ == '__main__':
    if len(sys.argv) != 3:
        print('Usage: python preprocess.py <raw-CSV> <service-port>')
        exit()
    use_stemmer = False
    csv_file_name = sys.argv[1]
    service_port = int(sys.argv[2])
    processed_file_name = sys.argv[1][:-4] + '-processed.csv'
    if use_stemmer:
        porter_stemmer = PorterStemmer()
        processed_file_name = sys.argv[1][:-4] + '-processed-stemmed.csv'
    preprocess_csv(csv_file_name, processed_file_name, service_port, test_file=False)
