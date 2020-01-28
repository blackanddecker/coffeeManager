import pymysql.cursors
import flask
from flask_api import status
import json 


def addShop(request , connection):
    data = request.get_json()
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    if 'name' not in data:
        return 'No \"name\"', status.HTTP_406_NOT_ACCEPTABLE

    try:       
        ## Find max id from DB
        with connection.cursor() as cursor:
            sql = "SELECT max(id) as idshop FROM shopmanager.shop"
            cursor.execute(sql)
            idshop = int(cursor.fetchall()[0]['idshop']) + 1
            print("Max product_id: " , idshop)

            sql = "INSERT INTO shopmanager.shop (id, name) VALUES ({}, '{}');".format(
                    idshop, str(data['name']) )
            cursor.execute(sql)
            connection.commit()

    except Exception as e: 
        print(e)
        return {"success":False}, status.HTTP_500_INTERNAL_SERVER_ERROR

    return {"success":True}, status.HTTP_200_OK