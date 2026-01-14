package com.example.RevaIssue.E2E.poms;

import com.example.RevaIssue.entity.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProjectPage extends ParentPOM {

    @FindBy(css = ".issue-card")
    private List<WebElement> issueList;

    public final String URL = "http://localhost:4200/project";
    public ProjectPage(WebDriver driver) {
        super(driver);
    }

    public void goToProject( int projectId){
        driver.get(URL + projectId);
    }

    // just get the first issue on the page to avoid having to search through the issueList
    public void selectFirstIssue(){
        issueList.getFirst().click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".issue-card.active")));
    }

    // click the update button on the issue you want to update
    public void clickUpdate() {
        WebElement firstIssue = driver.findElement(By.cssSelector(".issue-card.active"));
        firstIssue.findElement(By.cssSelector(".button_update button")).click();
    }

    public void updateIssue(String title,String description, int severity, int priority){
        // just get the first issue on the page to avoid having to search through the issueList
        selectFirstIssue();
        // click on the update button for the selected (first) issue
        clickUpdate();

        // update the fields of the Issue
        WebElement inputTitle = driver.findElement(By.cssSelector(".parent > input[type='text']"));
        inputTitle.sendKeys(title);

        WebElement inputDescription = driver.findElement(By.cssSelector(".parent textarea.description"));
        inputDescription.sendKeys(description);

        WebElement selectSeverity = driver.findElement(By.cssSelector(".parent select[name='severity']"));
        Select updatedSeverity = new Select(selectSeverity);
        updatedSeverity.selectByIndex(severity);

        WebElement selectPriority = driver.findElement(   By.cssSelector(".parent select[name='priority']"));
        Select updatedPriority = new Select(selectPriority);
        updatedPriority.selectByIndex(priority);

        driver.findElement(By.cssSelector(".parent .issue_buttons button")).click();



    }
}
