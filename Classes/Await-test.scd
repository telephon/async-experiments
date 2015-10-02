
// these examples are really limited to the narrow implementation
// they just show a working prototype.

// wait for user input
(
async {
	"waiting for boot...".postln;
	s.await.boot;
	s.notify;
	"waiting for Dialog ...".postln;
	Dialog.await.openPanel;
	"... and running a synth".postln;
	Synth.new(\default);
}
)



// wait for server and group
// note: for some reason, the server is not immediately responsive. wait here for 1 sec.
// (not necessary when s is running before already).
(
s.quit;
async {
	"waiting for boot...".postln;
	s.await.boot;
	s.dumpOSC(true);
	1.wait;
	"waiting for Group ...".postln;
	g = Group.await.new(s);
	"... and running the synth".postln;
	a = Synth.await.new(\default, [\freq, 8000], g);
	2.wait;
	a.await.release;
	g.free;
	s.dumpOSC(false);

}
)
