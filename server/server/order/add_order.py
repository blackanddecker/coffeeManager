import pymysql.cursors
import flask
from flask_api import status
import json 
import datetime



def add_order(request, connection):
    data = request.get_json()
    print(data)
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    if 'table_id' not in data:
        return 'No \"table_id\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'user_id' not in data:
        return 'No \"user_id\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'product_id' not in data:
        return 'No \"product_id\"', status.HTTP_406_NOT_ACCEPTABLE


    try: 
        with connection.cursor() as cursor:
            sql = "SELECT max(id) as id FROM shopmanager.order"
            cursor.execute(sql)
            order_id = int(cursor.fetchall()[0]['id']) + 1
            print("Max order_id: " , order_id)

            sql = "INSERT INTO shopmanager.order(id, date_order, status, table_id , user_id) VALUES\
             ({}, '{}', '{}', {}, {});".format(order_id, datetime.datetime.now(), 'pending', data['table_id'], data['user_id'])
            cursor.execute(sql)
            connection.commit()

            sql = "SELECT max(id) as id FROM shopmanager.orderinfo"
            cursor.execute(sql)
            order_info_id = int(cursor.fetchall()[0]['id']) + 1
            print("Max order_info_id: " , order_info_id)

            sql = "INSERT INTO shopmanager.orderinfo(id, details , product_id , order_id) VALUES\
             ({}, '{}', {}, {});".format(order_info_id, data['details'], data['product_id'], order_id)
            cursor.execute(sql)
            connection.commit()

    except Exception as e: 
        print ("Internal Error ", e)
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

    finally:
        connection.close()
    responce = 'OK'
    return responce, status.HTTP_200_OK