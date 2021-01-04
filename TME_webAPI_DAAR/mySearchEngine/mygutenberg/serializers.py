from rest_framework.serializers import ModelSerializer
from mygutenberg.models import ProduitDisponible, ProduitEnPromotion

class ProduitEnPromotionSerializer(ModelSerializer):
    class Meta:
        model = ProduitEnPromotion
        fields = ('id', 'tigID')

class ProduitDisponibleSerializer(ModelSerializer):
    class Meta:
        model = ProduitDisponible
        fields = ('id', 'tigID')
