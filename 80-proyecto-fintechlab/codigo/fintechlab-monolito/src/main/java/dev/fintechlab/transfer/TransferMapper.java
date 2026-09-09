package dev.fintechlab.transfer;
import org.mapstruct.Mapper;
@Mapper(componentModel="spring")
public interface TransferMapper { TransferResponse toResponse(TransferEntity source); }
