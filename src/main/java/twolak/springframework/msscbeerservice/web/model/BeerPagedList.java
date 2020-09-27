package twolak.springframework.msscbeerservice.web.model;

import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author twolak
 */
public class BeerPagedList extends PageImpl<BeerDto> {

    public BeerPagedList(List<BeerDto> content) {
        super(content);
    }

    public BeerPagedList(List<BeerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
