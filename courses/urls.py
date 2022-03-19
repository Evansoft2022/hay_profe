from django.conf.urls import url
from .views import *

urlpatterns=[
	url(r'^All_Courses/$',All_Courses,name="All_Courses"),
]