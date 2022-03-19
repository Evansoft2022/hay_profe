from django.db import models
from data.models import *


class Teacher(models.Model):
	documentI = models.CharField(max_length = 10,blank=True,null=True,default="")
	first_name = models.CharField(max_length = 20)
	second_name = models.CharField(max_length=20,default="",blank=True)
	surname = models.CharField(max_length=20)
	second_name = models.CharField(max_length=20,default="",blank=True)
	address = models.CharField(max_length=150)
	phone= models.CharField(max_length=10)
	email = models.EmailField(unique = True)
	password = models.CharField(max_length=20)
	block = models.BooleanField(default = True)
	description = models.TextField(default="",blank="")
	img = models.ImageField(upload_to = "Photo_Profile_Teacher",default="Photo_Profile_Teacher/photo.jpg")
	city = models.ForeignKey(City,on_delete = models.CASCADE,default=1)
	type = models.CharField(max_length=10,default= "Teacher")
	money = models.CharField(max_length = 10,default = "0")


	def __str__(self):
		return self.email


class Subjects(models.Model):
	name = models.CharField(max_length=90)
	teacher = models.ForeignKey(Teacher,on_delete = models.CASCADE,default="")

	def __str__(self):
		return self.name


















