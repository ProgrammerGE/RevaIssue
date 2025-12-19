package com.example.RevaIssue.entity

@Data
@NoArgsConstructor
@Entity
@Table(name = "issues")
public class Issue {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "issue_id", nullable = false)
private Long issueID;

@Column(name = "issue_description")
private String description;

@ManyToOne
@JoinColumn(name = "project_id")
private Project project;

@Column(name = "severity")
private Integer severity;

@Column(name = "priority")
private Integer priority;
}