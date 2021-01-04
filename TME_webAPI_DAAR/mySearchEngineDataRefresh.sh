cd mySearchEngine &&
source ../../myTidyVEnv/bin/activate &&
python3 manage.py refreshOnSaleList >> ~/mySearchEngineLog &&
python3 manage.py refreshAvailableList >> ~/mySearchEngineLog &&
deactivate
