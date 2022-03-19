from django.db import models
from student.models import Student
from teacher.models import Teacher


class Message_Email(models.Model):
	From = models.IntegerField(default=1)
	to = models.IntegerField(default=1)
	date = models.CharField(max_length=20)
	time = models.CharField(max_length=20)
	message = models.TextField()
	from_name = models.CharField(max_length=30,default="")
	to_name = models.CharField(max_length=30,default="")
	unread = models.BooleanField(default=False)
	subject = models.CharField(max_length=250,default="Saludo")
	trash = models.BooleanField(default=False)



class Attachments(models.Model):
	file = models.FileField(upload_to="Attachments")
	message_email = models.ForeignKey(Message_Email,on_delete=models.CASCADE)





class Chat_Privates(models.Model):
	sender = models.CharField(max_length=2)
	body = models.TextField()
	time = models.CharField(max_length=30)
	status = models.CharField(max_length=1)
	recvId = models.CharField(max_length=1)
	recvIsGroup = models.BooleanField(default = True)
	_id = models.CharField(max_length=100)
	_from= models.IntegerField()
	_to = models.IntegerField()












