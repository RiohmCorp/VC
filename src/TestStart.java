import org.kohsuke.github.*;

import java.io.IOException;

public class TestStart {

    public static void main(String[] args) throws IOException {
        //Initialise with personal access token
        GitHub git = new GitHubBuilder().withOAuthToken("${personal_access_token").build();
        //Get Organization
        GHOrganization organization = git.getOrganization("RiohmCorp");
        //Create Repository
        organization.createRepository("${repo_name}").create();
        //Get Repository
        GHRepository repo = git.getRepository("RiohmCorp/VC");

        //Get Branch
        String baseRef = repo.getBranch("main").getSHA1();
        //Create Branch - Branch cannot be created if it is a empty repository
        repo.createRef("refs/heads/branch_2", baseRef);

        //Create File
       repo.createContent()
                .branch("branch_1")
                .content("This is the file content")
                .path("File1")
                .message("Commit Message - Initial Commit")
                .commit();

       //Get file Blob SHA - without which we cannot update a file in the branch
        String fileSHA = repo.getFileContent("File1", "branch_1").getSha();

        //Update File
        repo.createContent()
                .branch("branch_1")
                .path("File1")
                .sha(fileSHA)
                .content("This is the updated file content")
                .message("Commit Message - Initial Commit 2")
                .commit();

        //Create Pull Request
        repo.createPullRequest("test request1", "branch_1", "branch_2", "merge request");

        //Get Pull Request
        GHPullRequest pR =  repo.getPullRequest(1);

        //Merge Pull Request
        pR.merge("Merged PR");
    }
}


