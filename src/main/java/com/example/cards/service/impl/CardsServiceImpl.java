package com.example.cards.service.impl;

import com.example.cards.constants.CardsConstants;
import com.example.cards.dto.CardsDTO;
import com.example.cards.entity.Cards;
import com.example.cards.exceptions.CardAlreadyExistsException;
import com.example.cards.exceptions.ResourceNotFoundException;
import com.example.cards.repository.CardsRepository;
import com.example.cards.service.ICardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import static com.example.cards.mapper.CardsMapper.mapToCards;
import static com.example.cards.mapper.CardsMapper.mapToCardsDTO;

@Service
public class CardsServiceImpl implements ICardsService {

    @Autowired
    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber)
    {
        Optional<Cards> optionalCards=cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent())
        {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);

        }
        Cards newCard=generateNewCard(mobileNumber);
        cardsRepository.save(newCard);
    }

    private Cards generateNewCard(String mobileNumber)
    {
        Cards newCard=new Cards();
        newCard.setMobileNumber(mobileNumber);
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        return newCard;
    }

    @Override
    public CardsDTO fetchCard(String mobileNumber) {
        CardsDTO cardsDTO=new CardsDTO();
        Cards cards=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Card","Mobile Number",mobileNumber));
        mapToCardsDTO(cards,cardsDTO);
        return cardsDTO;
    }

    @Override
    public boolean updateCard(CardsDTO cardsDTO) {
        Cards cards=cardsRepository.findByCardNumber(cardsDTO.getCardNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("Card","Card Number",cardsDTO.getCardNumber())
        );
        mapToCards(cardsDTO,cards);
        cardsRepository.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Card","Mobile Number",mobileNumber)
        );
        cardsRepository.deleteById(cards.getId());
        return true;
    }


}
