package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AirtelByWrapper extends By {
    By originalBy;
    String name;

    public AirtelByWrapper(String name, By originalBy) {
        this.originalBy = originalBy;
        this.name = name;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return originalBy.findElements(context);
    }

    @Override
    public WebElement findElement(SearchContext context) {
        return originalBy.findElement(context);
    }

    public String getDescription() {
        return this.name;
    }

    public By getOriginalBy() {
        return this.originalBy;
    }
}
