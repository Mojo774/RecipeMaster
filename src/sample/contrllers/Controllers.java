package sample.contrllers;

import sample.recipe_service.MainProcess;

// Интерфейс-флаг всех контроллеров, нужен для хранения данных о окнах
public interface Controllers {
    MainProcess mainProcess = new MainProcess();
}
