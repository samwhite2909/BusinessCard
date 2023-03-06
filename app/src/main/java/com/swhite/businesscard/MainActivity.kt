package com.swhite.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swhite.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CreateBusinessCard()
                }
            }
        }
    }
}

//Creates the main layout for the activity, showing the full business card.
@Composable
fun CreateBusinessCard() {
    //Holds the state of whether the projects are showing.
    val buttonClickedState = remember {
        mutableStateOf(false)
    }
    //Creates the background/base for the card.
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(390.dp)
                .padding(12.dp),
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.height(300.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Creates the contents of the business card.
                CreateProfileImage()
                Divider()
                CreateInfo()
                Button(onClick = {
                    buttonClickedState.value = !buttonClickedState.value
                }) {
                    //Shows correct text on button depending on state.
                    CheckButtonText(buttonClickedState)
                }
                //Shows or hides the portfolio depending on state.
                CheckPortfolio(buttonClickedState)
            }
        }
    }
}

//Shows the portfolio or hides it, depending on state.
@Composable
private fun CheckPortfolio(buttonClickedState: MutableState<Boolean>) {
    if (buttonClickedState.value) {
        Content()
    } else {
        Box {}
    }
}

//Shows the correct text on the button, depending on state.
@Composable
private fun CheckButtonText(buttonClickedState: MutableState<Boolean>) {
    if (buttonClickedState.value) {
        Text(
            text = stringResource(R.string.hide_portfolio),
            style = MaterialTheme.typography.button,
            )
    } else {
        Text(
            text = stringResource(R.string.show_portfolio),
            style = MaterialTheme.typography.button,
        )
    }
}

//Creates the portfolio or view of projects.
@Composable
fun Content() {
    //Creates the base for the portfolio.
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(corner = CornerSize(6.dp)),
            border = BorderStroke(width = 2.dp, color = Color.LightGray)
        ) {
            //Adds the projects into the portfolio.
            Portfolio(
                data = createProjects()
            )
        }
    }
}

//Takes in a list of projects and displays them individually in the equivalent of a RecyclerView.
@Composable
fun Portfolio(data: List<Project>) {
    LazyColumn {
        items(data) { item ->
            Card(
                modifier = Modifier
                    .padding(13.dp)
                    .fillMaxWidth(),
                shape = RectangleShape,
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .padding(7.dp)
                        .background(MaterialTheme.colors.surface)
                        .padding(16.dp)
                ) {

                    CreateProfileImage(modifier = Modifier.size(50.dp))
                    Column(
                        modifier = Modifier
                            .padding(7.dp)
                            .align(alignment = Alignment.CenterVertically)
                    ) {
                        Text(text = item.title, fontWeight = FontWeight.Bold)
                        Text(text = item.description, style = MaterialTheme.typography.body2)
                    }
                }
            }
        }
    }
}

//Creates the details displayed below the profile picture.
@Composable
private fun CreateInfo() {
    Column(modifier = Modifier.padding(5.dp)) {
        Text(
            text = stringResource(R.string.name),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.job_title),
            modifier = Modifier
                .padding(3.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.github_handle),
            modifier = Modifier
                .padding(3.dp)
                .align(alignment = Alignment.CenterHorizontally),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

//Creates the profile picture.
@Composable
private fun CreateProfileImage(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .size(150.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        elevation = 4.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = stringResource(R.string.profile_picture_description),
            modifier = modifier.size(135.dp),
            contentScale = ContentScale.Crop
        )
    }
}

//Allows for a full preview of the business card.
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BusinessCardTheme {
        CreateBusinessCard()
    }
}

//Creates the projects to be displayed within the portfolio.
//TODO: Externalise the strings and allow for custom pictures for each project.
fun createProjects(): List<Project> {
    val project1 = Project(
        "ImageViewMVVMApp",
        "Allows the user to search the internet for images based on a search term."
    )
    val project2 = Project(
        "PerfectFit",
        "A fitness app developed as part of my third year dissertation project."
    )
    val project3 = Project(
        "JavaGPSDemo",
        "A demo of accessing and using GPS data using Java."
    )
    return listOf(project1, project2, project3)
}