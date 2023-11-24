<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\StellingController;


Route::get('/home', [StellingController::class, 'home'])->name('home');
Route::get('/stelling/maak', [StellingController::class, 'maak'])->name('maak');
Route::post('/stelling/store', [StellingController::class, 'store'])->name('store');
Route::post('/stelling/join', [StellingController::class, 'join'])->name('join');
Route::get('/stelling/{id}', [StellingController::class, 'show'])->name('show');



<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\Stelling;
use App\Models\Optie;
use Illuminate\Http\Request;

class StellingController extends Controller
{
    public function store(Request $request)
    {
        $request->validate([
            'titel' => 'required',
            'beschrijving' => 'required',
            'opties.*' => 'nullable|string',
        ]);
        $stelling = Stelling::create([
            'title' => $request->titel,
            'beschrijving' => $request -> beschrijving,
        ]);

        foreach($request->opties ?? [] as $optie)
        {
            Optie::create([
                'S_id' => $stelling->id,
                'optie' => $optie,
            ]);
        }
        return redirect()->route('home');
    }

    public function home(){
        return view('home');
    }

    public function maak(){
        $stelling = Stelling::all();
        return view('create', compact('stelling'));
    }

    public function show($id) {
        $stelling = Stelling::with('opties')->findOrFail($id);
        return view('view', compact('stelling'));
    }
    public function join() {
        return view('join');
    }

}
