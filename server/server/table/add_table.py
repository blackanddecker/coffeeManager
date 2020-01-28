import pymysql.cursors
import flask
from flask_api import status
import json 


def add_table(request , connection):
    data = request.get_json()
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    if 'shop_id' not in data:
        return 'No \"shop_id\"', status.HTTP_406_NOT_ACCEPTABLE

    try:       
        ## Find max id from DB
        with connection.cursor() as cursor:
            sql = "SELECT max(id) as id FROM shopmanager.table"
            cursor.execute(sql)
            max_id = int(cursor.fetchall()[0]['id']) + 1
            print("Max id: " , max_id)
            
            sql = "SELECT count(id) FROM shopmanager.table WHERE shop_id = {}".format(int(data['shop_id']))
            cursor.execute(sql)
            no_table = cursor.fetchall()[0]['count(id)'] + 1
            print("Max no_table: " , no_table)


            sql = "INSERT INTO shopmanager.table (id, no_table , shop_id) VALUES ({},{},{});".format(
                    max_id, no_table, int(data['shop_id']) )
            cursor.execute(sql)
            connection.commit()

    except Exception as e: 
        print(e)
        return {"success":False}, status.HTTP_500_INTERNAL_SERVER_ERROR

    responce = 'OK'
    return {"success":True}, status.HTTP_200_OK