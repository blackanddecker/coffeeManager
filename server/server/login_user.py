import pymysql.cursors
import flask
from flask_api import status
import json 
import datetime

def login_user(request , connection):
    data = request.get_json()
    print(data)
    result =[]
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    
    try:       
        with connection.cursor() as cursor:
            sql = "SELECT * FROM shopmanager.employee WHERE email ='{}' AND password='{}' AND status='admin'".format(data['email'], data['password'])
            print(sql)
            cursor.execute(sql)
            result = cursor.fetchall()
            if len(result) == 1 :
                return result, status.HTTP_200_OK
            else: 
                return [], status.HTTP_200_OK

    except Exception as e: 
        print ("Internal Error ", e)
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

    finally:
        connection.close()

    return result, status.HTTP_200_OK