import pymysql.cursors
import flask
from flask_api import status
import json 
import datetime

def select_order(request, connection):
    data = request.get_json()
    print(data)
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    
    try:
        #all orders
        if ('table_id' not in data) and ('order_date' not in data) and ('status' not in data) and ('user_id' not in data):
            with connection.cursor() as cursor:
                sql = "SELECT * FROM shopmanager.order o , shopmanager.table t WHERE o.table_id = t.id AND t.shop_id ={}".format(data['shop_id'])
                cursor.execute(sql)
                result = cursor.fetchall()
        #table
        elif ('order_date' not in data) and ('status' not in data) and ('user_id' not in data):
            with connection.cursor() as cursor:
                sql = "SELECT * FROM shopmanager.order o , shopmanager.table t WHERE o.table_id = t.id AND t.shop_id ={} AND t.id = {}".format(data['shop_id'], data['table_id'])
                cursor.execute(sql)
                result = cursor.fetchall()
        #status
        elif ('table_id' not in data) and ('order_date' not in data) and ('user_id' not in data):
            with connection.cursor() as cursor:
                sql = "SELECT * FROM shopmanager.order o , shopmanager.table t WHERE o.table_id = t.id AND t.shop_id ={} AND o.status='{}'".format(data['shop_id'], data['status'])
                cursor.execute(sql)
                result = cursor.fetchall()
        #table and status
        elif ('order_date' not in data) and ('user_id' not in data):
            with connection.cursor() as cursor:
                sql = "SELECT * FROM shopmanager.order o , shopmanager.table t WHERE o.table_id = t.id AND t.shop_id ={} AND o.status='{}' AND t.id={}".format(data['shop_id'], data['status'], data['table_id'])
                cursor.execute(sql)
                result = cursor.fetchall()

    except Exception as e: 
        print ("Internal Error ", e)
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

    return result, status.HTTP_200_OK