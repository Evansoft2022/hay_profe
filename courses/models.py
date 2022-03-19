from django.db import models
from teacher.models import Teacher

class Courses(models.Model):
	img = models.ImageField(upload_to = "Img_Courses")
	duration = models.IntegerField()
	teacher = models.ForeignKey(Teacher,on_delete=models.CASCADE)
	title = models.CharField(max_length = 50)







