package be.dabla.bus;

class Unmanaged implements Manageable {
    static final Manageable UNMANAGED = new Unmanaged();

    private Unmanaged() {}

    @Override
    public boolean isEnabled() {
        return true;
    }
}
