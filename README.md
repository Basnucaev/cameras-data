В задании было указано что по ссылке: `http://www.mocky.io/v2/5c51b9dd3400003252129fb5`, мы получаем список доступных видеокамер, поэтому я не стал делать возможность менять id запроса.

Сделано:
1. Ускорил обработку запросов на внешнее API через @Async
2. Ускорил агрегацию данных через @Async и ParallelStream
3. Покрыл Unit тестами 90% всего кода
4. Настроил Dockerfile, на случай, если захотите потестить

