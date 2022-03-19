from django.contrib import admin
from django.urls import path
from django.conf.urls import url, include
from django.conf.urls.static import static
from django.conf import settings

urlpatterns = [
    path('admin/', admin.site.urls),
    url(r'^', include('home.urls')),
    url(r'^teacher/', include('teacher.urls')),
    url(r'^student/', include('student.urls')),
    url(r'^chat/', include('chat.urls')),
    url(r'^courses/', include('courses.urls')),
    url(r'^wallet/', include('wallet.urls')),
    url(r'^data/', include('data.urls')),
]

urlpatterns += static(settings.MEDIA_URL, document_root = settings.MEDIA_ROOT)