package vlite.digikamweb.ui.view.editor.editalbums

import com.github.mvysny.karibudsl.v10.column
import com.github.mvysny.karibudsl.v10.grid
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import vlite.digikamweb.domain.objects.Album
import vlite.digikamweb.ui.base.view.OnRowSelected
import vlite.digikamweb.ui.base.view.defaultConfiguration

data class EditAlbumsGridRow(override val album: Album) : Album.Holder

fun VerticalLayout.editAlbumsGrid(
    onRowSelected: OnRowSelected<EditAlbumsGridRow>
) : Grid<EditAlbumsGridRow> {
    return grid<EditAlbumsGridRow> {

        defaultConfiguration(onRowSelected)

        column( { row ->
            row.album.name.value
        }) {
            //setHeader("")
        }

    }
}


