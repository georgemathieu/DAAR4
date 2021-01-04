import requests
from rest_framework.views import APIView
from rest_framework.response import Response
from mygutenberg.config import baseUrl,gutenbergUrl,gutendexUrl

# Create your views here.

class RedirectionListeDeLivresFrancais(APIView):
    def get(self, request, format=None):
        response = requests.get(gutendexUrl+'books?languages=fr')
        jsondata = response.json()
        return Response(jsondata)

class RedirectionListeDeLivresAnglais(APIView):
    def get(self, request, format=None):
        response = requests.get(gutendexUrl+'books?languages=en')
        jsondata = response.json()
        return Response(jsondata)

class RedirectionDetailLivreFrancais(APIView):
    def get_object(self, pk):
        try:
            response = requests.get(gutendexUrl+'books?languages=fr&ids='+str(pk))
            jsondata = response.json()
            return Response(jsondata)
        except:
            raise Http404
    def get(self, request, pk, format=None):
        response = requests.get(gutendexUrl+'books?languages=fr&ids='+str(pk))
        jsondata = response.json()
        return Response(jsondata)

class RedirectionDetailLivreAnglais(APIView):
    def get_object(self, pk):
        try:
            response = requests.get(gutendexUrl+'books?languages=en&ids='+str(pk))
            jsondata = response.json()
            return Response(jsondata)
        except:
            raise Http404
    def get(self, request, pk, format=None):
        response = requests.get(gutendexUrl+'books?languages=en&ids='+str(pk))
        jsondata = response.json()
        return Response(jsondata)


class RedirectionDetailLivre(APIView):
    def get_object(self, pk):
        try:
            response = requests.get(gutendexUrl+'books/'+str(pk)+'/')
            jsondata = response.json()
            return Response(jsondata)
        except:
            raise Http404
    def get(self, request, pk, format=None):
        response = requests.get(gutendexUrl+'books/'+str(pk)+'/')
        jsondata = response.json()
        return Response(jsondata)

class RedirectionListeDeLivres(APIView):
    def get(self, request, format=None):
        response = requests.get(gutendexUrl+'books/')
        jsondata = response.json()
        return Response(jsondata)


class RedirectionListeDeProduits(APIView):
    def get(self, request, format=None):
        response = requests.get(baseUrl+'products/')
        jsondata = response.json()
        return Response(jsondata)
#    def post(self, request, format=None):
#        NO DEFITION of post --> server will return "405 NOT ALLOWED"


class RedirectionDetailProduit(APIView):
    def get_object(self, pk):
        try:
            response = requests.get(baseUrl+'product/'+str(pk)+'/')
            jsondata = response.json()
            return Response(jsondata)
        except:
            raise Http404
    def get(self, request, pk, format=None):
        response = requests.get(baseUrl+'product/'+str(pk)+'/')
        jsondata = response.json()
        return Response(jsondata)
#    def put(self, request, pk, format=None):
#        NO DEFITION of put --> server will return "405 NOT ALLOWED"
#    def delete(self, request, pk, format=None):
#        NO DEFITION of delete --> server will return "405 NOT ALLOWED"

class RedirectionListePointsRelais(APIView):
    def get(self, request, format=None):
        response = requests.get(baseUrl+'shipPoints/')
        jsondata = response.json()
        return Response(jsondata)

class RedirectionDetailPointRelais(APIView):
    def get_object(self, pk):
        try:
            response = requests.get(baseUrl+'shipPoint/'+str(pk)+'/')
            jsondata = response.json()
            return Response(jsondata)
        except:
            raise Http404
    def get(self, request, pk, format=None):
        response = requests.get(baseUrl+'shipPoint/'+str(pk)+'/')
        jsondata = response.json()
        return Response(jsondata)


from mygutenberg.models import ProduitEnPromotion, ProduitDisponible
from mygutenberg.serializers import ProduitEnPromotionSerializer, ProduitDisponibleSerializer
from django.http import Http404
from django.http import JsonResponse

class PromoList(APIView):
    def get(self, request, format=None):
        res=[]
        for prod in ProduitEnPromotion.objects.all():
            serializer = ProduitEnPromotionSerializer(prod)
            response = requests.get(baseUrl+'product/'+str(serializer.data['tigID'])+'/')
            jsondata = response.json()
            res.append(jsondata)
        return JsonResponse(res, safe=False)
#    def post(self, request, format=None):
#        NO DEFITION of post --> server will return "405 NOT ALLOWED"

class DisponibleList(APIView):
    def get(self, request, format=None):
        res=[]
        for prod in ProduitDisponible.objects.all():
            serializer = ProduitDisponibleSerializer(prod)
            response = requests.get(baseUrl+'product/'+str(serializer.data['tigID'])+'/')
            jsondata = response.json()
            res.append(jsondata)
        return JsonResponse(res, safe=False)

class PromoDetail(APIView):
    def get_object(self, pk):
        try:
            return ProduitEnPromotion.objects.get(tigID=pk)
        except ProduitEnPromotion.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        prod = self.get_object(pk)
        serializer = ProduitEnPromotionSerializer(prod)
        response = requests.get(baseUrl+'product/'+str(serializer.data['tigID'])+'/')
        jsondata = response.json()
        return Response(jsondata)
#    def put(self, request, pk, format=None):
#        NO DEFITION of put --> server will return "405 NOT ALLOWED"
#    def delete(self, request, pk, format=None):
#        NO DEFITION of delete --> server will return "405 NOT ALLOWED"
