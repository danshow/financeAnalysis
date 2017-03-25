__author__='Bill Dan'
import tushare as ts
import pymongo
from pymongo import MongoClient
import json
# conn=pymongo.Connection('127.0.0.1',port=27017)
client = MongoClient()
client = MongoClient('mongodb://47.92.39.111:27017/')
db=client.refdata
print 'start ...'
df =ts.get_latest_news(top=5,show_content=True)


#get current year shibor
#df = ts.shibor_data(2014) #get 2014 year shibor
# df.sort('date', ascending=False).head(10)
db.shibor_data.insert(json.loads(df.to_json(orient='records')))

print 'success'