from django.urls import path
from mygutenberg import views

urlpatterns = [
    path('products/', views.RedirectionListeDeProduits.as_view()),
    path('product/<int:pk>/', views.RedirectionDetailProduit.as_view()),
    path('onsaleproducts/', views.PromoList.as_view()),
    path('onsaleproduct/<int:pk>/', views.PromoDetail.as_view()),
    path('shipPoints/', views.RedirectionListePointsRelais.as_view()),
    path('shipPoint/<int:pk>/', views.RedirectionDetailPointRelais.as_view()),
    path('availableproducts/', views.DisponibleList.as_view()),
    path('book/<int:pk>/', views.RedirectionDetailLivre.as_view()),
    path('books/', views.RedirectionListeDeLivres.as_view()),
    path('englishbook/<int:pk>/', views.RedirectionDetailLivreAnglais.as_view()),
    path('englishbooks/', views.RedirectionListeDeLivresAnglais.as_view()),
    path('frenchbook/<int:pk>/', views.RedirectionDetailLivreFrancais.as_view()),
    path('frenchbooks/', views.RedirectionListeDeLivresFrancais.as_view()),
]
