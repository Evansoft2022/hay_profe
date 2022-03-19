from django.conf.urls import url
from .views import *

urlpatterns=[
	url(r'^All_Teacher/$',All_Teacher,name="All_Teacher"),
	url(r'^Teacher_Profile/(\d+)/$',Teacher_Profile,name="Teacher_Profile"),
	url(r'^Sent_Message/(\d+)/$',Sent_Message,name="Sent_Message"),
]