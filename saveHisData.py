__author__='Bill Dan'
import tushare as ts
import pymongo
from pymongo import MongoClient
import json
# conn=pymongo.Connection('127.0.0.1',port=27017)
client = MongoClient()
client = MongoClient('mongodb://47.92.39.111:27017/')
db=client.refdata
df=ts.get_tick_data('600848',date='2014-12-22')
print 'save to db..'
db.tickdata.insert(json.loads(df.to_json(orient='records')))
print 'completed'
