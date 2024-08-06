package SpringBootStarterProject.Trippackage.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TripStatus {
    NOT_STARTED("1"),
    Available("2"),
    Completed("3"),
    InProgress("4"),
    Finished("5");

    @Getter
    private final String value;

    public final String getAllValues(){
        return TripStatus.values()[this.ordinal()].toString();
    }

}
