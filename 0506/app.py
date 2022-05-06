from datetime import datetime

from flask import Flask, render_template, jsonify, request
from pymongo import MongoClient

app = Flask(__name__)

client = MongoClient("mongodb://localhost:27017/")
db = client.test


@app.route('/')
def index():
    return render_template('index.html')

@app.route('/detail/<idx>')
def detail(idx):
    article = db.article.find_one({'idx': int(idx)}, {'_id': False})
    return render_template("detail.html", article=article)

@app.route('/articleList', methods=['GET'])
def get_article_list():
    order = request.args.get('order')
    if order == "desc":
        article_list = list(db.article.find({}, {'_id': False}).sort([("read_count", -1)]))
    else:
        article_list = list(db.article.find({}, {'_id': False}).sort([("reg_date", -1)]))

    for article in article_list:
        article['reg_date'] = article['reg_date'].strftime('%Y.%m.%d %H:%M:%S')

    return jsonify({"article_list": article_list})

# Create
@app.route('/article', methods=['POST'])
def create_article():
    title = request.form.get('title')
    content = request.form.get('content')
    pw = request.form.get('pw')
    article_count = db.article.estimated_document_count({})

    if article_count == 0:
        max_value = 1
    else:
        max_value = db.article.find_one(sort=[("idx", -1)])['idx'] + 1
        # max_value = (list(db.test.find({}).sort([("idx", -1)])))[0]['idx'] + 1

    article = {
        'idx': max_value,
        'title': title,
        'content': content,
        'pw': pw,
        'read_count': 0,
        'reg_date': datetime.now()
    }
    db.article.insert_one(article)
    return {"result": "success"}

# Read
@app.route('/article', methods=['GET'])
def read_article():
    idx = request.args['idx']
    db.article.update_one({'idx': int(idx)}, {'$inc': {'read_count': 1}})
    article = db.article.find_one({'idx': int(idx)}, {'_id': False})
    return jsonify({"article": article})

# Update
@app.route('/article', methods=['PUT'])
def update_article():
    idx = request.form.get('idx')
    title = request.form.get('title')
    content = request.form.get('content')

    db.article.update_one({'idx': int(idx)}, {'$set': {'title': title, 'content': content}})
    return {"result": "success"}

# Delete
@app.route('/article', methods=['DELETE'])
def delete_article():
    idx = request.args.get('idx')
    db.article.delete_one({'idx': int(idx)})
    return {"result": "success"}

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)