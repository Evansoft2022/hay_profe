from django.conf.urls import url
from .views import *

urlpatterns=[
	url(r'^All_Students/$',All_Students,name="All_Students"),
	url(r'^Profile_Student/(\d+)/$',Profile_Student,name="Profile_Student"),
]