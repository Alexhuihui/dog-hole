package top.alexmmd.dog.domain.entity;

import lombok.Data;

@Data
public class Project {

    // ID
    private int id;

    // Owner ID
    private int ownerId;

    // Name of the project
    private String name;

    // Project description
    private String description;

    // URL of the project cover image
    private String cover;

    // Price of the project
    private double price;

    // Status of the project (e.g., RECRUITING, ...)
    private String status;

    // Publication time of the project
    private long pubTime;

    // Duration of the project in days
    private int duration;

    // Number of project applications
    private int applyCount;

    // Creation time of the project
    private long createdAt;

    // Specific role for the project
    private String specificRole;

    // Flag to indicate if the project can be bargained
    private boolean bargain;

    // Text representation of the project status
    private String statusText;

    // Text representation of the project type
    private String typeText;

    // Number of visits to the project page
    private int visitCount;

    // Flag to indicate if the project can be refreshed
    private boolean canRefresh;

    // Type of the project (e.g., WEAPP, ...)
    private String type;

    // Type of developer (e.g., TEAM, ...)
    private String developerType;
}
