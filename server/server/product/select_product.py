import pymysql.cursors
import flask
from flask_api import status
import json 
import datetime

def select_product(shop_id, connection):

    try:       
        with connection.cursor() as cursor:
            sql = "SELECT * FROM shopmanager.product WHERE shop_id={}".format(shop_id)
            cursor.execute(sql)
            result = cursor.fetchall()
    except Exception as e: 
        print ("Internal Error ", e)
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

    return result, status.HTTP_200_OK