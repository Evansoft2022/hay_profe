from django.conf.urls import url
from .views import *

urlpatterns=[
	url(r'^$',Login,name="Login"),
	url(r'^Register/$',Register,name="Register"),
	url(r'^Home/$',Home,name="Home"),
	url(r'^LogOut/$',LogOut,name="LogOut"),
	url(r'^Setting_Profile/(\d+)/$',Setting_Profile,name="Setting_Profile"),
	url(r'^UnLock/$',UnLock,name="UnLock"),
]