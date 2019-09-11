import pymysql.cursors
import flask
from flask_api import status
import json 
import datetime


def update_paid_order(request, connection):
    data = request.get_json()
    print(data)
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    if 'order_id' not in data:
        return 'No \"order_id\"', status.HTTP_406_NOT_ACCEPTABLE

    try: 
        with connection.cursor() as cursor:
            sql = "UPDATE shopmanager.order SET status='paid', date_paid='{}' WHERE id={};".format(datetime.datetime.now(),data['order_id'])
            cursor.execute(sql)
            connection.commit()

    except Exception as e: 
        print ("Internal Error ", e)
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

    responce = 'OK'
    return responce, status.HTTP_200_OK