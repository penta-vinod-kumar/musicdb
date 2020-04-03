package com.elsevier.musicdb.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DiscogsResponse {
    List<Result> results = new ArrayList<>();
}
