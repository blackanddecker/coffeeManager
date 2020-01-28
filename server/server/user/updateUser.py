import pymysql.cursors
import flask
from flask_api import status
import json 
import datetime

def updateUser(request , connection):
    data = request.get_json()
    print(data)
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    if 'id' not in data:
        return 'No \"id\"', status.HTTP_406_NOT_ACCEPTABLE
    
    try:
        with connection.cursor() as cursor:
            sql = "UPDATE shopmanager.employee SET name='{}', status='{}' , password = '{}' WHERE id={}".format(data['name'],data['status'],data['password'],data['id'])
            print(sql)
            cursor.execute(sql)
            connection.commit()
        responce = {"success":True}
        return responce, status.HTTP_200_OK
    except Exception as e: 
        print ("Internal Error ", e)
        return {"success":False}, status.HTTP_500_INTERNAL_SERVER_ERROR

