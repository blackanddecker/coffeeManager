import pymysql.cursors
import flask
from flask_api import status
import json 


def select_table(shop_id , connection):
    try:       
        with connection.cursor() as cursor:
            sql = "SELECT * FROM shopmanager.table WHERE shop_id ={};".format(shop_id)
            cursor.execute(sql)
            result = cursor.fetchall()
    except Exception as e: 
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

    finally:
        connection.close()
    return result, status.HTTP_200_OK