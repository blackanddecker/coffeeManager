import pymysql.cursors
import flask
from flask_api import status
import json 


def selectShop(request , connection):
    if request.method != 'GET':
        return '', status.HTTP_406_NOT_ACCEPTABLE


    try:       
        with connection.cursor() as cursor:
            sql = "SELECT * FROM shopmanager.shop;"
            cursor.execute(sql)
            result = cursor.fetchall()
    except Exception as e: 
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

    finally:
        connection.close()
    return result, status.HTTP_200_OK