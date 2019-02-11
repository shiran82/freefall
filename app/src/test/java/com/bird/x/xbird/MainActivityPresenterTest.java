package com.bird.x.xbird;

import com.bird.x.xbird.database.FallRecord;
import com.bird.x.xbird.presenter.MainActivityPresenter;
import com.bird.x.xbird.repository.MainActivityDataRepository;
import com.bird.x.xbird.screen.MainActivityMVP;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {
    @Mock
    private MainActivityDataRepository mockRepository;
    @Mock
    private MainActivityMVP mockMvpView;
    private MainActivityPresenter presenter;

    @Before
    public void setUp() {

        presenter = new MainActivityPresenter(mockRepository, mockMvpView);
    }

    @Test
    public void testFetchFalls() {

        List<FallRecord> fallRecords = new ArrayList();
        FallRecord fallRecord = new FallRecord();
        fallRecord.setMilliseconds(System.currentTimeMillis());
        fallRecords.add(fallRecord);

        when(mockRepository.fetchFallListLocally()).thenReturn(fallRecords);

        presenter.fetchFallList();

        verify(mockMvpView).showFalls(anyList());
    }

    @Test
    public void testFetchEmptyList() {

        presenter.fetchFallList();

        verify(mockMvpView, never()).showFalls(anyList());
    }

    @Test
    public void testCheckFallStarted() {

        presenter.checkFall(0.8, false, System.currentTimeMillis(), System
                .currentTimeMillis());

        verify(mockMvpView).freeFallState(eq(true), anyLong());
        verify(mockMvpView, never()).showToast(anyLong());
    }

    @Test
    public void testCheckFallInProgress() {

        presenter.checkFall(0.8, true, System.currentTimeMillis(), System
                .currentTimeMillis());

        verify(mockMvpView, never()).freeFallState(eq(true), anyLong());
        verify(mockMvpView, never()).showToast(anyLong());
    }

    @Test
    public void testCheckFallFinished() {

        presenter.checkFall(12, true, System.currentTimeMillis(), System
                .currentTimeMillis());

        verify(mockMvpView).freeFallState(eq(false), anyLong());
        verify(mockMvpView).showToast(anyLong());
    }

    @Test
    public void testCheckFallNotStartedNorInProgress() {

        presenter.checkFall(1, false, System.currentTimeMillis(), System
                .currentTimeMillis());

        verify(mockMvpView, never()).freeFallState(anyBoolean(), anyLong());
        verify(mockMvpView, never()).showToast(anyLong());
    }

}