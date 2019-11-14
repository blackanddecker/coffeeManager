# ShopManager Server Api calls 

1. **User**
   - GET /select/userId 
   - POST /updateUser
   - POST /addUser 
   - POST /login_user
  
2. **Product**
   - GET /select_product/shopId 
   - POST /add_product
   - POST /update_product

3. **Order**
   - POST /add_order
   - POST /update_delivered_order
   - POST /update_paid_order
   - POST /select_order

4. **Shop**
   - GET /selectShop/shopId
   - POST /addShop


5. **Table** 
   - GET /select_table/{shop_id}
   - POST /add_table

## **USER**

GET /select/userId 

response: 
```

{
    "email": <unique str>,
    "id": <unique id>,
    "name": <str>,
    "password": ********,
    "shop_id": <unique id>,
    "status": <admin/ user>
}

```


POST /updateUser

request 
```
{
	"status":<admin/ user>, 
	"name": <str>, 
	"id":<unique id>
}
```

response : 

```
{   "success": <boelean>   }
```

POST /addUser 

```
{
    "email": <unique str>,
    "id": <unique id>,
    "name": <str>,
    "password": ********,
    "shop_id": <unique id>,
    "status": <admin/ user>
}
```
response

```
{   "success": <boelean>   }
```

POST  /login_user

```
{
    "email": <unique str>,
    "password": ********,
}
```

response: 

```
{
    "email": <unique str>,
    "id": <unique id>,
    "name": <str>,
    "password": ********,
    "shop_id": <unique id>,
    "status": <admin/ user>
}
```


## **PRODUCT**
-------------------------------------------------
GET /select_product/shopId 
response:

```
 [
    {
        "name":<str>,
        "price":<float>,
        "available":<int>,
        "shop_id":<unique int>,
        "details":<str>
    }
]
```

POST /add_product

request
```
{
	"name":<str>,
	"price":<float>,
	"available":<int>,
	"shop_id":<unique int>,
	"details":<str>
}
```
response: 

```
{   "success": <boelean>   }
```

POST /update_product

request
```
{
	"name":<str>,
	"price":<float>,
	"available":<int>,
	"shop_id":<unique int>,
	"details":<str>
}
```
response: 

```
{   "success": <boelean>   }
```


## **ORDER**
-------------------------------------------------
POST /add_order

request:
```
{
	"table_id":<unique int>,
	"user_id":<unique int>,
	"orderHistory":[{"product_id":<unique int>, "details":<str>}, 
                        {"product_id":<unique int>, "details":<str>}],
	"order_id":-1
} 
```

__if order_id != -1 we add item to excisting order
else we create a new order__

response: 

```
{   "success": <boelean>   }
```



POST /update_delivered_order

request 

``` 
{   "order_id":<unique id>  }
```

response: 

```
{   "success": <boelean>   }
```


POST /update_paid_order

``` 
{   "order_id":<unique id>  }
```

response: 

```
{   "success": <boelean>   }
```

-------------------------------------------------
POST /select_order

request
```
{
	"shop_id":<unique id>, 
	"start_date":<date>,
	"end_date":<date>,
	"status": <admin/employee>,
	"employee": <list on names>,
	"table": <list on table>,
    "products": <list on products>
}
```

response : 

```
[
    {
        "date_delivered": <date>,
        "date_order": <date>,
        "date_paid": <date>,
        "employee_id": <unique id>,,
        "id": <unique id>,
        "no_table": <int>,
        "o.table_id": <int>,
        "orderHistory": [
            {
                "details": <str>,
                "name": <str>,
                "o_info": <int>,
                "order_id": <int>,
                "product_id": <int>
            },
            {
                "details": <str>,
                "name": <str>,
                "o_info": <int>,
                "order_id": <int>,
                "product_id": <int>
            }
        ],
        "price": <int>,
        "status": <delivered/paid>,
        "table_id": <int>
    },
]
```

## **SHOP** 
-------------------------------------------------

POST /addShop

request 

```
{   "name": <unique str>  }
```

response: 

```
{   "success": <boelean>   }
```


GET /selectShop/shopId

response : 
```
{
    "id": <unique id>,
    "name": <unique str>
}
```




## **TABLE** 
-------------------------------------------------
GET /select_table/{shop_id}



POST /add_table

request 

``` 
{   "shop_id":<unique id>  }
```
response: 

```
{   "success": <boelean>   }
```



