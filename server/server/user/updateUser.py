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
    if 'idemployee' not in data:
        return 'No \"idemployee\"', status.HTTP_406_NOT_ACCEPTABLE
    
    try:
        with connection.cursor() as cursor:
            sql = "UPDATE shopmanager.employee SET name='{}', status='{}' WHERE idemployee={}".format(data['name'],data['status'],data['idemployee'])
            cursor.execute(sql)
            connection.commit()
        responce = 'OK'
        return responce, status.HTTP_200_OK
    except Exception as e: 
        print ("Internal Error ", e)
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

