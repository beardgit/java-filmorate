package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.storage.MpaDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaServiceImplementation implements MpaService {
    private final MpaDbStorage mpaDbStorage;

    @Override
    public List<MpaDto> getAllMpa() {
        return mpaDbStorage.getAllMpa();
    }

    @Override
    public MpaDto getMpaById(int id) {
        return mpaDbStorage.getMpaById(id);
    }
}