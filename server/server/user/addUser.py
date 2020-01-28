import pymysql.cursors
import flask
from flask_api import status
import json 


def addUser(request , connection):
    data = request.get_json()
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    if 'name' not in data:
        return 'No \"name\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'shop_id' not in data:
        return 'No \"shop_id\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'status' not in data:
        return 'No \"status\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'email' not in data:
        return 'No \"email\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'password' not in data:
        return 'No \"password\"', status.HTTP_406_NOT_ACCEPTABLE
    try:       
        ## Find max id from DB
        with connection.cursor() as cursor:
            sql = "SELECT max(id) as id FROM shopmanager.employee"
            print(sql)
            cursor.execute(sql)
            max_id = int(cursor.fetchall()[0]['id']) + 1
            print("Max product_id: " , max_id)

            sql = "INSERT INTO shopmanager.employee (id, name , status, shop_id , email, password) VALUES ({}, '{}', '{}', {},'{}','{}');".format(
                    max_id, str(data['name']) , str(data['status']), int(data['shop_id']), str(data['email']), str(data['password']))
            cursor.execute(sql)
            connection.commit()

    except Exception as e: 
        print(e)
        return {"success":False}, status.HTTP_200_OK

    responce = 'OK'
    return {"success":True}, status.HTTP_200_OK