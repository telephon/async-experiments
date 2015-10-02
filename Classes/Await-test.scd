

// wait for user input
(
async {
	"waiting for boot...".postln;
	s.await.boot;
	s.notify;
	"waiting for Dialog ...".postln;
	Dialog.await.openPanel;
	"... and running a synth".postln;
	a = Synth.new(\default);
}
)



// wait for server and group
(
s.quit;
async {
	"waiting for boot...".postln;
	s.await.boot;
	1.wait;
	s.notify;
	s.dumpOSC(true);
	"waiting for Group ...".postln;
	g = Group.await.new(s);
	"... and running the synth".postln;
	a = Synth.await.new(\default, g.object);
	2.wait;
	a.release;
	s.dumpOSC(false);
}
)
