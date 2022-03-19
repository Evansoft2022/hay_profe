from django.conf.urls import url
from .views import *

urlpatterns=[
	url(r'^Inbox/$',Inbox,name="Inbox"),
	url(r'^Email_Sent/$',Email_Sent,name="Email_Sent"),
	url(r'^Compose_Email/(\d+)/$',Compose_Email,name="Compose_Email"),
	url(r'^View_Email/(\d+)/$',View_Email,name="View_Email"),
	url(r'^Trash_Email/$',Trash_Email,name="Trash_Email"),
	url(r'^Trash/$',Trash,name="Trash"),
	url(r'^Delete_Email/$',Delete_Email,name="Delete_Email"),
	url(r'^Chat_Private/$',Chat_Private,name="Chat_Private"),
]