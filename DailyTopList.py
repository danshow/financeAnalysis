__author__='Bill Dan'
import tushare as ts
import pymongo
from pymongo import MongoClient
import json
# conn=pymongo.Connection('127.0.0.1',port=27017)
client = MongoClient()
client = MongoClient('mongodb://localhost:27017/')
db=client.refdata
print 'start...'
df=ts.top_list('2017-03-21')
db.top_list.insert(json.loads(df.to_json(orient='records')))
print 'completed'