from flask import Blueprint,render_template,redirect,url_for,session,request
from database import*
import uuid
# import demjson

import smtplib
from email.mime.text import MIMEText
from flask_mail import Mail
import random


api=Blueprint('api',__name__)
@api.route('/login/',methods=['get','post'])
def login():
	data={}
	data.update(request.args)
	username = request.args['username']
	password = request.args['password']
	q="SELECT * FROM `login` WHERE `username`='%s' AND `password` ='%s'"%(username,password)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  str(data)
@api.route('/register/',methods=['get','post'])
def register():
	data={}
	fname=request.args['fname']
	lname=request.args['lname']

	gender=request.args['gender']
	age=request.args['age']
	weight=request.args['weight']
	height=request.args['height']
	phone=request.args['phone']
	email=request.args['email']
	username=request.args['username']
	password=request.args['password']
	q1="SELECT * FROM login WHERE `username`='%s'"%(username)
	res=select(q1)
	if res:
		data['status']="duplicate"
		data['method']="Customer_registration"
	else:
	
		q="Insert into login values (null,'%s','%s','pending')"%(username,password)
		ids=insert(q)
		print(q)
		q="INSERT INTO `users` VALUES(NULL,'%s','%s','%s','%s','%s','%s','%s','%s','%s','0','pending')"%(ids,fname,lname,age,gender,weight,height,phone,email)
		uid=insert(q)
		print(q)
		if uid:

			email = email
			loginid = ids

			otp = random.randint(1000, 9999)
			# email=email
			# print(email)
			pwd = "YOUR OTP : " + str(otp)
			print(pwd)
			try:
				gmail = smtplib.SMTP('smtp.gmail.com', 587)
				gmail.ehlo()
				gmail.starttls()
				gmail.login('handoutfoodcommunity@gmail.com', 'xkyokbuzwopremsz')
			except Exception as e:
				print("Couldn't setup email!!" + str(e))

			pwd = MIMEText(pwd)

			pwd['Subject'] = 'One Time Password'

			pwd['To'] = email

			pwd['From'] = 'handoutfoodcommunity@gmail.com.com'

			try:
				gmail.send_message(pwd)
				print(pwd)
				data['status'] = "success"

			except Exception as e:
				print("COULDN'T SEND EMAIL", str(e))
			else:
				data['status'] = "failed"

			data['status'] = "success"
			data['data_o'] = otp
			data['data1'] = ids

			data['status']="Success"
			data['user_id']=uid
	return str(data)

@api.route('/viewpayment/',methods=['get','post'])
def viewpayment():
	data={}
	q="SELECT * FROM `batches`"
	res=select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='batch'


	return str(data)
@api.route('/payment/',methods=['get','post'])
def payment():
	data={}
	batch_id=request.args['batch_id']
	user_id=request.args['user_id']
	amount=request.args['amount']

	q="INSERT INTO `payments` VALUES(NULL,'%s','%s','Fee',CURDATE())"%(user_id,amount)
	insert(q)
	q="UPDATE `users` SET `batch_id`='%s',`payment_status`='Paid' WHERE `user_id`='%s'"%(batch_id,user_id)
	update(q)
	q="UPDATE `login` SET `user_type`='user' WHERE `login_id`=(SELECT `login_id` FROM `users` WHERE `user_id`='%s')"%(user_id)
	update(q)
	print(q)
	data['status']="success"
	data['method']='paid'
	

	return str(data)
	
@api.route('/equipments/',methods=['get','post'])
def equipments():
	data={}

	q="SELECT * FROM `equipments`"
	res=select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return str(data)


	
@api.route('/vequipments/',methods=['get','post'])
def vequipments():
	data={}
	equipment_id=request.args['equipment_id']
	q="SELECT * FROM `workouts` WHERE `equipment_id`='%s'"%(equipment_id)
	res=select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return str(data)
@api.route('/payments/',methods=['get','post'])
def payments():
	data={}
	log_id=request.args['log_id']
	amount=request.args['amount']
	q="INSERT INTO `payments` VALUES(NULL,'%s','%s','Subscription',CURDATE())"%(log_id,amount)
	insert(q)
	q="update users set payment_status='Paid' where user_id='%s'"%(log_id)
	update(q)
	data['status']  = 'success'

	return str(data)
@api.route('/viewdiet/',methods=['get','post'])
def viewdiet():
	data={}
	log_id=request.args['log_id']
	q="SELECT *,CONCAT(`first_name`,' ',`last_name`) AS physician_name FROM `user_diets` INNER JOIN `physician` USING(`physician_id`) WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(log_id)
	res=select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return str(data)
@api.route('/vmessage/',methods=['get','post'])
def vmessage():
	data={}
	log_id=request.args['log_id']
	q="SELECT * FROM `message` WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(log_id)
	res=select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'

	data['method']='vmessage'
	return str(data)
@api.route('/smessage/',methods=['get','post'])
def smessages():
	data={}
	log_id=request.args['log_id']
	physician_id=request.args['physician_id']
	
	message=request.args['message']

	q="INSERT INTO `message` VALUES(NULL,(SELECT `user_id` FROM `users` WHERE `login_id`='%s'),'%s','%s','pending',CURDATE())"%(log_id,physician_id,message)
	insert(q)
	data['status']  = 'success'
	data['method']='smessage'


	
	return str(data)
@api.route('/vcomplaint/',methods=['get','post'])
def vcomplaint():
	data={}
	log_id=request.args['log_id']
	q="SELECT * FROM `complaints`  WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(log_id)
	res=select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'

	data['method']='view'
	return str(data)
@api.route('/scomplaint/',methods=['get','post'])
def scomplaint():
	data={}
	log_id=request.args['log_id']
	message=request.args['message']

	q="INSERT INTO `complaints` VALUES (NULL,(SELECT `user_id` FROM `users` WHERE `login_id`='%s'),'%s','pending',CURDATE())"%(log_id,message)
	insert(q)
	data['status']  = 'success'
	data['method']='send'

	return str(data)

@api.route('/vfeedback/',methods=['get','post'])
def vfeedback():
	data={}
	log_id=request.args['log_id']
	q="SELECT * FROM `feedback`  WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(log_id)
	res=select(q)
	if res:
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'

	data['method']='view'
	return str(data)

@api.route('/sfeedback/',methods=['get','post'])
def sfeedback():
	data={}
	log_id=request.args['log_id']
	
	
	message=request.args['message']

	q="INSERT INTO `feedback` VALUES (NULL,(SELECT `user_id` FROM `users` WHERE `login_id`='%s'),'%s','pending',CURDATE())"%(log_id,message)
	insert(q)
	data['status']  = 'success'
	data['method']='send'


	
	return str(data)




@api.route('/View_products',methods=['get','post'])
def View_products():
	data={}
	data['method']='View_products'
	category_id=request.args['category_id']
	q="SELECT * FROM `products` INNER JOIN `category` USING(`category_id`) where category_id='%s'"%(category_id)
	print(q)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	return str(data)


@api.route('/Add_to_cart',methods=['get','post'])
def Add_to_cart():
    data={}

    loginid=request.args['loginid']
    product_id=request.args['product_id']
    qnty=request.args['qnty']
    amount=request.args['amount']

    flag=0
    q="Select * from bookingmaster  WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s') and status='Pending'"%(loginid)
    result = select(q)
    if not result:
        q="INSERT INTO `bookingmaster` VALUES(null,(select user_id from users where login_id='%s'),'%s',now(),'Pending')"%(loginid,amount)
        id=insert(q)

    else:
        id=result[0]['bmaster_id']
        flag=1
	
    q1="Select * from bookingmaster inner join bookingchild using(bmaster_id)  WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s') and status='Pending' and product_id='%s'"%(loginid,product_id)
    res1=select(q1)

    if res1:
        qw="UPDATE `bookingchild` SET `quantity`=`quantity`+'%s',amount=amount+'%s' WHERE `bmaster_id`='%s'"%(qnty,amount,result[0]['bmaster_id'])
        update(qw)
    else:
        qs="INSERT INTO `bookingchild`(`bmaster_id`,`product_id`,`quantity`,`amount`) VALUES('%s','%s','%s','%s')"%(id,product_id,qnty,amount)
        insert(qs)
    if flag==1:
        qf="UPDATE `bookingmaster` SET `total`=`total`+'%s' WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s') and status='Pending'"%(amount,loginid)
        update(qf)
  
    data['status']  = 'success'
    data['method']='Add_to_cart'
    return  str(data)


@api.route('/View_cart_items',methods=['get','post'])
def View_cart_items():
	data={}
	
	login_id = request.args['login_id']
 
	q="SELECT *,`bookingchild`.`quantity` AS bqnty,`bookingmaster`.`status` AS bstatus FROM `bookingmaster` INNER JOIN `bookingchild` USING(`bmaster_id`) INNER JOIN `products` USING(`product_id`) INNER JOIN `category` USING(`category_id`) WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s') AND `bookingmaster`.`status` ='Pending'"%(login_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		# q="select total from bookingmaster WHERE `user_id`=(SELECT `user_id` FROM `user` WHERE `login_id`='%s')"%(login_id)
		# select(q)
		data['status']  = 'success'
		data['bid']=res[0]['bmaster_id']
		data['data'] = res
		
	else:
		data['status']  = 'failed'
	data['method']='View_cart_items'
	return  str(data)



@api.route('/userremoveproduct',methods=['get','post'])
def userremoveproduct():
	data={}
	
	bchild_ids = request.args['bchild_ids']
	amounts = request.args['amounts']
	bmaster_ids = request.args['bmaster_ids']
 
	q="delete from bookingchild WHERE `bchild_id`='%s'"%(bchild_ids)
	print(q)
	delete(q)
	q="UPDATE `bookingmaster` SET `total`=`total`-'%s' WHERE `bmaster_id`='%s'"%(amounts,bmaster_ids)
	update(q)
	
	data['status']  = 'success'
	data['method']='userremoveproduct'
	return  str(data)



@api.route('/userremoveallproduct',methods=['get','post'])
def userremoveallproduct():
	data={}
	
	login_id = request.args['login_id']
	bmaster_ids = request.args['bmaster_ids']
 
	q="DELETE FROM `bookingmaster` WHERE `user_id`=(SELECT `user_id` FROM users WHERE login_id='%s') AND `status`='Pending'"%(login_id)
	print(q)
	delete(q)
	q="DELETE FROM `bookingchild` WHERE `bmaster_id`='%s'"%(bmaster_ids)
	delete(q)
	
	data['status']  = 'success'
	data['method']='userremoveallproduct'
	return  str(data)


@api.route('/User_payment',methods=['get','post'])
def User_payment():

	data={}
	bmaster_ids=request.args['bmaster_ids']
	amount=request.args['amount']
	

	q="UPDATE `bookingmaster` SET `status`='Paid' WHERE `bmaster_id`='%s'"%(bmaster_ids)
	update(q)
	q="SELECT * FROM `bookingmaster` INNER JOIN `bookingchild` USING(`bmaster_id`) WHERE `bmaster_id`='%s'"%(bmaster_ids)
	res=select(q)
	for row in res:
		q="UPDATE `products` SET `quantity`=`quantity`-'%s' WHERE `product_id`='%s'"%(row['quantity'],row['product_id'])
		update(q)
	q= "INSERT INTO `payment` VALUES(NULL,'%s','%s',CURDATE(),'NA')"%(bmaster_ids,amount)   #added NA for paymet 
	print(q)	
	id=insert(q)
	
	if id>0:
		data['status'] = 'success'
		
	else:
		data['status'] = 'failed'
	data['method'] = 'User_payment'
	return str(data)


@api.route('/View_my_orders',methods=['get','post'])
def View_my_orders():
	data={}

	login_id=request.args['login_id']
	
	q="SELECT *,`payment`.`total` AS ptotal,`payment`.`date` AS pdate,`bookingmaster`.`status` AS pstatus FROM `bookingmaster`  INNER JOIN `payment` on bookingmaster.bmaster_id=payment.bmaster_id WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s') "%(login_id)
	res = select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='View_my_orders'
	return  str(data)


@api.route('/View_order_products',methods=['get','post'])
def View_order_products():
	data={}
	bmaster_id=request.args['bmaster_id']

	q="SELECT *,`bookingchild`.`quantity` AS bquantity FROM `bookingmaster` INNER JOIN `bookingchild` USING(`bmaster_id`) INNER JOIN `products` USING(`product_id`) INNER JOIN `category` USING(`category_id`) WHERE `bmaster_id`='%s'"%(bmaster_id)
	res = select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='View_order_products'
	return  str(data)



@api.route('/View_tournament',methods=['get','post'])
def View_tournament():
	data={}

	q="select * from tournament"
	res=select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='View_tournament'
	return  str(data)





@api.route('/User_view_employee',methods=['get','post'])
def User_view_employee():
	data={}

	q="select * from gym_instructor"
	res=select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='User_view_employee'
	return  str(data)




###############################################################





@api.route('/User_view_category',methods=['get','post'])
def User_view_category():
	data={}

	q="select * from category"
	res=select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='User_view_category'
	return  str(data)



@api.route('/User_send_tournament_request',methods=['get','post'])
def User_send_tournament_request():
	data={}

	tournament_ids=request.args['tournament_ids']
	login_id=request.args['login_id']
	q="SELECT * FROM `tournament_request` WHERE `tournament_id`='%s' AND `user_id`=(select user_id from users where login_id='%s')"%(tournament_ids,login_id)
	rt=select(q)
	if rt:
		data['status']="failed"
	else:
		qq="INSERT INTO `tournament_request` VALUES(NULL,'%s',(SELECT user_id FROM users WHERE login_id='%s'),'Pending',CURDATE())"%(tournament_ids,login_id)
		insert(qq)

		data['status']  = 'success'

	data['method']='User_send_tournament_request'
	return  str(data)



@api.route('/View_my_request',methods=['get','post'])
def View_my_request():
	data={}

	login_id=request.args['login_id']
	q="SELECT * FROM `tournament_request` INNER JOIN `tournament` USING(`tournament_id`) WHERE `user_id`=(SELECT `user_id` FROM users WHERE login_id='%s')"%(login_id)
	res=select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='View_my_request'
	return  str(data)



@api.route('/User_view_physician',methods=['get','post'])
def User_view_physician():
	data={}

	q="select * from physician"
	res=select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='User_view_physician'
	return  str(data)


@api.route('/User_plan_payment/',methods=['get','post'])
def User_plan_payment():
	data={}
	log_id=request.args['log_id']
	amount=request.args['amount']
	plan_id=request.args['plan_id']
	plan_type=request.args['plan_type']

	q="insert into plan_payment values(null,'%s',(select user_id from users where login_id='%s'),'%s',curdate())"%(plan_id,log_id,amount)
	insert(q)
	data['status']  = 'success'
	
	data['method']='User_plan_payment'
	return  str(data)


@api.route('/User_view_plan',methods=['get','post'])
def User_view_plan():
	data={}
	

	q="select * from plan"
	res=select(q)
	data['data']=res
	data['status']  = 'success'
	
	data['method']='User_view_plan'
	return  str(data)




@api.route('/User_rate_employee/',methods=['get','post'])
def User_rate_employee():
	data={}
	provider_id=request.args['provider_id']
	
	rate=request.args['rate']
	desca=request.args['desca']
	log_id=request.args['log_id']

	q="select * from `review` where `instructor_id`='%s' and user_id=(select `user_id` from `users` where `login_id`='%s')"%(provider_id,log_id)
	res=select(q)
	if res:
		qq="UPDATE `review` SET `review_desc`='%s',`rating`='%s',`review_date`=CURDATE() WHERE `review_id`='%s'"%(desca,rate,res[0]['review_id'])
		update(qq)
	else:
		q="INSERT INTO `review` VALUES(NULL,(select `user_id` from `users` where `login_id`='%s'),'%s','%s','%s',curdate())"%(log_id,provider_id,desca,rate)
		insert(q)
	data['status']  = 'success'
	data['method']='send'
	return str(data)

@api.route('/User_view_rate_employee/',methods=['get','post'])
def User_view_rate_employee():
	data={}
	provider_id=request.args['provider_id']
	log_id=request.args['log_id']
	q="select * from `review` where `instructor_id`='%s' and user_id=(select `user_id` from `users` where `login_id`='%s')"%(provider_id,log_id)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='view'
	return str(data)




@api.route('/Driver_forgot_password',methods=['get','post'])
def Driver_forgot_password():
	data={}
	# data.update(request.args)
	uname = request.args['uname']
	email = request.args['email']
	phone = request.args['phone']

	q = "SELECT * FROM `login` INNER JOIN `users` USING(`login_id`) WHERE `email`='%s' AND `phone`='%s' AND `username`='%s' AND `user_type`='user'"%(email,phone,uname)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  str(data)


@api.route('/Driver_set_new_password',methods=['get','post'])
def Driver_set_new_password():
	data={}
	loginid = request.args['loginid']
	password = request.args['password']
	c_password = request.args['c_password']

	if password==c_password:
		q="UPDATE `login` SET `password`='%s' WHERE `login_id`='%s'"%(c_password,loginid)
		update(q)
		data['status']  = 'success'
	else:
		data['status']	= 'failed'
	return str(data)

@api.route('/Customer_make_payment')
def Customer_make_payment():
	data={}
	log_id=request.args['log_id']
	amounts=request.args['amounts']
	product_id=request.args['product_id']
	qnty=request.args['qnty']
	q="INSERT INTO `bookingmaster` VALUES(NULL,(SELECT `user_id` FROM `users` WHERE `login_id`='%s'),'%s',CURDATE(),'paid')"%(log_id,amounts)
	vals=insert(q)
	q="INSERT INTO `bookingchild` VALUES(NULL,'%s','%s','%s','%s')"%(vals,product_id,qnty,amounts)
	insert(q)
	q="INSERT INTO `payment` VALUE(NULL,'%s','%s',CURDATE(),'NA')"%(vals,amounts)
	print(q)
	res=insert(q)
	
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="Customer_make_payment"
	return str(data)




@api.route('/user_view_attendance')
def user_view_attendance():
	data={}
	log_id=request.args['login_id']
	
	
	q="SELECT * FROM `attendance` WHERE `user_id`=(SELECT `user_id` FROM users WHERE login_id='%s') ORDER BY `attendance_id` DESC"%(log_id)
	print(q)
	res=select(q)
	
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="user_view_attendance"
	return str(data)



@api.route('/User_view_batch')
def User_view_batch():
	data={}
	logid=request.args['logid']
	q="SELECT * FROM `batches` INNER JOIN `gym_instructor` USING(`instructor_id`) INNER JOIN `users` USING(`batch_id`) WHERE `users`.`login_id`='%s'"%(logid)
	print(q)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="User_view_batch"
	return str(data)



@api.route('/User_view_workouts')
def User_view_workouts():
	data={}
	logid=request.args['logid']
	q="SELECT *,workouts.description as wdescription FROM `user_workouts` INNER JOIN `workouts` USING(`workout_id`) INNER JOIN `equipments` USING(`equipment_id`) INNER JOIN `users` USING(`user_id`) WHERE `login_id`='%s'"%(logid)
	print(q)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="User_view_workouts"
	return str(data)