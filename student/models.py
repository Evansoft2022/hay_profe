from django.db import models

class Student(models.Model):
	first_name = models.CharField(max_length = 20)
	second_name = models.CharField(max_length=20,default="",null=True,blank=True)
	surname = models.CharField(max_length=20)
	second_name = models.CharField(max_length=20,default="",null=True,blank=True)
	address = models.CharField(max_length=150)
	phone= models.CharField(max_length=10)
	email = models.EmailField(unique = True)
	password = models.CharField(max_length=20)
	block = models.BooleanField(default = False)
	img = models.ImageField(upload_to = "Photo_Profile_Student",default="Photo_Profile_Student/photo.jpg")
	type = models.CharField(max_length=10,default= "Student")

	def __str__(self):
		return self.email






